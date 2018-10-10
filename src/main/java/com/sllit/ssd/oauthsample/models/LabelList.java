package com.sllit.ssd.oauthsample.models;

import java.util.List;

/**
 * Created by dinukshakandasamanage on 10/10/18.
 */
public class LabelList {

    private List<Label> labels;

    public LabelList(List<Label> labels) {
        this.labels = labels;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabelList labelList = (LabelList) o;

        return labels != null ? labels.equals(labelList.labels) : labelList.labels == null;
    }

    @Override
    public int hashCode() {
        return labels != null ? labels.hashCode() : 0;
    }
}
