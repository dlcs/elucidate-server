package com.digirati.elucidate.model.annotation.selector.dataposition;

import java.io.Serializable;

import com.digirati.elucidate.model.annotation.selector.AbstractAnnotationSelector;

@SuppressWarnings("serial")
public class AnnotationDataPositionSelector extends AbstractAnnotationSelector implements Serializable {

    private Integer start;
    private Integer end;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
