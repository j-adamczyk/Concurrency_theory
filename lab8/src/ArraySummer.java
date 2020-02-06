import java.util.concurrent.RecursiveTask;

public class ArraySummer extends RecursiveTask<Double>
{
    double[] data;
    int start;
    int end;
    int countThreshold;

    ArraySummer(double[] data, int start, int end, int countThreshold)
    {
        this.data = data;
        this.start = start;
        this.end = end;
        this.countThreshold = countThreshold;
    }

    @Override
    protected Double compute()
    {
        double sum = 0;
        if ((end - start) < countThreshold)
        {
            for (int i = start; i < end; i++)
                sum += data[i];
        }
        else
        {
            int middle = (start + end) / 2;

            ArraySummer subtaskA = new ArraySummer(data, start, middle, countThreshold);
            ArraySummer subtaskB = new ArraySummer(data, middle, end, countThreshold);

            subtaskA.fork();
            subtaskB.fork();

            sum += subtaskA.join() + subtaskB.join();
        }

        return sum;
    }
}
