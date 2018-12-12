package Shared;

import java.io.Serializable;
import java.util.List;

public class Review implements Serializable {
    int     score;
    String  reviewText;
    User    reviewer;
    Album   reviewed;

    public Review(int score, String reviewText, User reviewer, Album reviewed) {
        this.score = score;
        this.reviewText = reviewText;
        this.reviewer = reviewer;
        this.reviewed = reviewed;
    }

    public static Review newReview(User self, Album toReview) {
        String text = inputUtil.promptStr("Review:\n");
        int score = inputUtil.promptIntBound(10, 0, "Score: ", false);
        return new Review(score, text, self, toReview);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Album getReviewed() {
        return reviewed;
    }

    public void setReviewed(Album reviewed) {
        this.reviewed = reviewed;
    }
}