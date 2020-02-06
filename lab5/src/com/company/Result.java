package com.company;

// data class for holding data for printing after simulation
class Result
{
    private int maxIter;
    private int threadNo;
    private int taskNo;

    private int avgTime;
    private int stDev;

    Result(int maxIter, int threadNo, int taskNo, int avgTime, int stDev)
    {
        this.setMaxIter(maxIter);
        this.setThreadNo(threadNo);
        this.setTaskNo(taskNo);
        this.setAvgTime(avgTime);
        this.setStDev(stDev);
    }

    public int getMaxIter()
    {
        return maxIter;
    }

    public void setMaxIter(int maxIter)
    {
        this.maxIter = maxIter;
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

    public int getAvgTime()
    {
        return avgTime;
    }

    public void setAvgTime(int avgTime)
    {
        this.avgTime = avgTime;
    }

    public int getStDev()
    {
        return stDev;
    }

    public void setStDev(int stDev)
    {
        this.stDev = stDev;
    }
}
