package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MandelbrotSimulator
{
    private int threadNo;
    private int taskNo;

    private int maxIter;
    private int height;
    private int width;
    private double zoom;

    private BufferedImage image;

    private double zx, zy, cX, cY, tmp;

    MandelbrotSimulator(int threadNo, int taskNo, int maxIter, int height, int width, double zoom)
    {
        this.setThreadNo(threadNo);
        this.setTaskNo(taskNo);

        this.setMaxIter(maxIter);
        this.height = height;
        this.width = width;
        this.zoom = zoom;

        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    void simulate() throws ExecutionException, InterruptedException
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(getThreadNo());
        List<Future<Integer>> codes = new ArrayList<>();

        if (this.getTaskNo() != (height * width))
        {
            int step = height / (getTaskNo() * getThreadNo());
            int y1 = 0;
            int y2;

            for (int i = 0; (i + 1) < (getTaskNo() * getThreadNo()); i++)
            {
                y2 = y1 + step - 1;
                MandelbrotWorker worker = new MandelbrotWorker(
                        0, y1, width - 1, y2,
                        this.getMaxIter(), this.width, this.height, this.zoom, this.image);
                Future<Integer> value = threadPool.submit(worker);
                codes.add(value);
                y1 += step;
            }
            MandelbrotWorker worker = new MandelbrotWorker(
                    0, y1, width - 1, height - 1,
                    this.getMaxIter(), this.width, this.height, this.zoom, this.image);
            Future<Integer> value = threadPool.submit(worker);
            value.get();
        }
        else
        {
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                {
                    MandelbrotWorker worker = new MandelbrotWorker(
                            x, y, x, y, this.getMaxIter(), this.width, this.height, this.zoom, this.image);
                    Future<Integer> value = threadPool.submit(worker);
                    codes.add(value);
                }
        }

        for (Future<Integer> value: codes)
            value.get();

        threadPool.shutdown();
    }

    public int getThreadNo()
    {
        return threadNo;
    }

    public void setThreadNo(int threadNo)
    {
        this.threadNo = threadNo;
    }

    public int getTaskNo()
    {
        return taskNo;
    }

    public void setTaskNo(int taskNo)
    {
        this.taskNo = taskNo;
    }

    public int getMaxIter()
    {
        return maxIter;
    }

    public void setMaxIter(int maxIter)
    {
        this.maxIter = maxIter;
    }

    void showImage()
    {
        JFrame editorFrame = new JFrame("Mandelbrot");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(this.image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
    }
}
