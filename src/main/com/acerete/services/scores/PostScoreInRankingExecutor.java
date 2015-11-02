package com.acerete.services.scores;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.acerete.Configuration;
import com.acerete.services.scores.ScoresServiceImpl.PostScoreInRankingRunnable;

public class PostScoreInRankingExecutor {

	private final static long MINUTES_TO_WAIT_UNTIL_TERMINATION = 120l;
	
    private ExecutorService executor = null;
 
    public PostScoreInRankingExecutor() {
    	
    	int queueSize = Configuration.getInstance().getServerPosterQueueSize();
		int corePoolSize = Configuration.getInstance().getServerPosterCorePoolSize();
		int maxPoolSize = Configuration.getInstance().getServerPosterMaxPoolSize();
		
        try {
        	BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueSize);
        	executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS, queue);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void execute(PostScoreInRankingRunnable postScoreInRankingRunnable) throws InterruptedException {
		try {
			executor.execute(postScoreInRankingRunnable);
		}
		catch (RejectedExecutionException e) {
			e.printStackTrace();
			// Wait 1 second and try again
			Thread.sleep(1000);
			//TODO: Try only N times
			execute(postScoreInRankingRunnable);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		executor.shutdown();
		executor.awaitTermination(MINUTES_TO_WAIT_UNTIL_TERMINATION, TimeUnit.MINUTES);
		super.finalize();
	}

}
