package com.company.pipeline;

// dummy class to represent data for pipeline
public class PipelineData
{
    private int ID;
    private int iter;

    public PipelineData(int ID, int iter)
    {
        this.ID = ID;
        this.iter = iter;
    }

    public PipelineData(int id)
    {
        this(id, 0);
    }

    public void incrementIteration()
    {
        this.iter += 1;
    }

    @Override
    public String toString() { return "(ID=" + this.ID + ", iter=" + this.iter + ")"; }
}