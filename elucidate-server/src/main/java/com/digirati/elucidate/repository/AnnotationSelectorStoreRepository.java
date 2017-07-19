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

    AnnotationCSSSelector createAnnotationCssSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson);

    List<AnnotationCSSSelector> deleteAnnotationCssSelectors(Integer bodyPK, Integer targetPK);

    AnnotationDataPositionSelector createAnnotationDataPositionSelector(Integer bodyPK, Integer targetPK, String selectorIri, Integer start, Integer end, String selectorJson);

    List<AnnotationDataPositionSelector> deleteAnnotationDataPositionSelectors(Integer bodyPK, Integer targetPK);

    AnnotationFragmentSelector createAnnotationFragmentSelector(Integer bodyPK, Integer targetPK, String selectorIri, String conformsTo, String value, Integer x, Integer y, Integer w, Integer h, Integer start, Integer end, String selectorJson);

    List<AnnotationFragmentSelector> deleteAnnotationFragmentSelectors(Integer bodyPK, Integer targetPK);

    AnnotationSVGSelector createAnnotationSvgSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson);

    List<AnnotationSVGSelector> deleteAnnotationSvgSelectors(Integer bodyPK, Integer targetPK);

    AnnotationTextPositionSelector createAnnotationTextPositionSelector(Integer bodyPK, Integer targetPK, String selectorIri, Integer start, Integer end, String selectorJson);

    List<AnnotationTextPositionSelector> deleteAnnotationTextPositionSelectors(Integer bodyPK, Integer targetPK);

    AnnotationTextQuoteSelector createAnnotationTextQuoteSelector(Integer bodyPK, Integer targetPK, String selectorIri, String exact, String prefix, String suffix, String selectorJson);

    List<AnnotationTextQuoteSelector> deleteAnnotationTextQuoteSelectors(Integer bodyPK, Integer targetPK);

    AnnotationXPathSelector createAnnotationXPathSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson);

    List<AnnotationXPathSelector> deleteAnnotationXPathSelectors(Integer bodyPK, Integer targetPK);
}
