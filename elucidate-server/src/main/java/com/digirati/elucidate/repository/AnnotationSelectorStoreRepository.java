package com.digirati.elucidate.repository;

import java.util.List;

import com.digirati.elucidate.model.annotation.selector.css.AnnotationCSSSelector;
import com.digirati.elucidate.model.annotation.selector.dataposition.AnnotationDataPositionSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.AnnotationFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.svg.AnnotationSVGSelector;
import com.digirati.elucidate.model.annotation.selector.textposition.AnnotationTextPositionSelector;
import com.digirati.elucidate.model.annotation.selector.textquote.AnnotationTextQuoteSelector;
import com.digirati.elucidate.model.annotation.selector.xpath.AnnotationXPathSelector;

public interface AnnotationSelectorStoreRepository {

    public AnnotationCSSSelector createAnnotationCssSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson);

    public List<AnnotationCSSSelector> deleteAnnotationCssSelectors(Integer bodyPK, Integer targetPK);

    public AnnotationDataPositionSelector createAnnotationDataPositionSelector(Integer bodyPK, Integer targetPK, String selectorIri, Integer start, Integer end, String selectorJson);

    public List<AnnotationDataPositionSelector> deleteAnnotationDataPositionSelectors(Integer bodyPK, Integer targetPK);

    public AnnotationFragmentSelector createAnnotationFragmentSelector(Integer bodyPK, Integer targetPK, String selectorIri, String conformsTo, String value, Integer x, Integer y, Integer w, Integer h, Integer start, Integer end, String selectorJson);

    public List<AnnotationFragmentSelector> deleteAnnotationFragmentSelectors(Integer bodyPK, Integer targetPK);

    public AnnotationSVGSelector createAnnotationSvgSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson);

    public List<AnnotationSVGSelector> deleteAnnotationSvgSelectors(Integer bodyPK, Integer targetPK);

    public AnnotationTextPositionSelector createAnnotationTextPositionSelector(Integer bodyPK, Integer targetPK, String selectorIri, Integer start, Integer end, String selectorJson);

    public List<AnnotationTextPositionSelector> deleteAnnotationTextPositionSelectors(Integer bodyPK, Integer targetPK);

    public AnnotationTextQuoteSelector createAnnotationTextQuoteSelector(Integer bodyPK, Integer targetPK, String selectorIri, String exact, String prefix, String suffix, String selectorJson);

    public List<AnnotationTextQuoteSelector> deleteAnnotationTextQuoteSelectors(Integer bodyPK, Integer targetPK);

    public AnnotationXPathSelector createAnnotationXPathSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson);

    public List<AnnotationXPathSelector> deleteAnnotationXPathSelectors(Integer bodyPK, Integer targetPK);
}
