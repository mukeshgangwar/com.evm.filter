package com.evm.filter;

public class InvalidVoters {
    private String voterId;
    private String candidate_id;
    private boolean result;
    private boolean excptedResult;

    public String getVoterId() {
        return voterId;
    }

    public InvalidVoters setVoterId(String voterId) {
        this.voterId = voterId;
        return this;
    }

    public String getCandidate_id() {
        return candidate_id;
    }

    public InvalidVoters setCandidate_id(String candidate_id) {this.candidate_id = candidate_id;
    return this;
    }

    public boolean getResult() {
        return result;
    }

    public InvalidVoters setResult(boolean result) {
        this.result = result;
        return this;
    }

    public boolean getExcptedResult() {
        return excptedResult;
    }

    public InvalidVoters setExcptedResult(boolean excptedResult) {
        this.excptedResult = excptedResult;
        return this;
    }
}
