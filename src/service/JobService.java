package service;

import java.util.ArrayList;
import java.util.List;

import model.Job;

public class JobService {

    // Collection using Generics
    private List<Job> jobs = new ArrayList<>();

    // Add Job
    public void addJob(Job job) {
        jobs.add(job);
    }

    // Get all Jobs
    public List<Job> getAllJobs() {
        return jobs;
    }

    // Search Job by ID
    public Job searchJob(int jobId) {

        for (Job job : jobs) {

            if (job.getJobId() == jobId) {
                return job;
            }
        }

        return null;
    }

    // Generic Method
    public <T> void printList(List<T> data) {

        for (T item : data) {
            System.out.println(item);
        }
    }
}