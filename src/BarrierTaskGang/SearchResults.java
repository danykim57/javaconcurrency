package BarrierTaskGang;

import java.util.ArrayList;
import java.util.List;

public class SearchResults {

    public class Result {

        public int mIndex;

        public Result(int index) {
            mIndex = index;
        }
    }

    public long mThreadId;

    public String mWord;

    public String mInputData;

    public long mCycle;

    protected List<Result> mList;

    public SearchResults() {
        mList = null;
    }

    public SearchResults(long threadId,
                         long cycle,
                         String word,
                         String inputData) {
        mThreadId = threadId;
        mCycle = cycle;
        mWord = word;
        mInputData = inputData;
        mList = new ArrayList<Result>();
    }

    public String toString() {
        return
                "["
                + mThreadId
                + "|"
                + mCycle
                + "] "
                + mWord
                + " at "
                + mInputData;
    }

    public void add(int index) {
        mList.add(new Result(index));
    }

    public boolean isEmpty() {
        return mList.size() == 0;
    }

    void print() {
        if (!isEmpty()) {
            System.out.print(toString());

            for (Result result : mList)
                System.out.print(
                        "["
                        + result.mIndex
                        + "]");
            System.out.println("");
        }
    }
}
