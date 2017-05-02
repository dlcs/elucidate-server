package com.digirati.elucidate.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationCSSSelectorRowMapper;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationDataPositionSelectorRowMapper;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationFragmentSelectorRowMapper;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationSVGSelectorRowMapper;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationTextPositionSelectorRowMapper;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationTextQuoteSelectorRowMapper;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationXPathSelectorRowMapper;
import com.digirati.elucidate.model.annotation.selector.css.AnnotationCSSSelector;
import com.digirati.elucidate.model.annotation.selector.dataposition.AnnotationDataPositionSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.AnnotationFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.svg.AnnotationSVGSelector;
import com.digirati.elucidate.model.annotation.selector.textposition.AnnotationTextPositionSelector;
import com.digirati.elucidate.model.annotation.selector.textquote.AnnotationTextQuoteSelector;
import com.digirati.elucidate.model.annotation.selector.xpath.AnnotationXPathSelector;
import com.digirati.elucidate.repository.AnnotationSelectorStoreRepository;

@Repository(AnnotationSelectorStoreRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationSelectorStoreRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationSelectorStoreRepository {

    public static final String REPOSITORY_NAME = "annotationSelectorStoreRepositoryJDBCImpl";

    @Autowired
    public AnnotationSelectorStoreRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationCSSSelector createAnnotationCssSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson) {
        String sql = "SELECT * FROM annotation_css_selector_create(?, ?, ?, ?, ?)";
        Object[] params = {bodyPK, targetPK, selectorIri, value, selectorJson};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationCSSSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationCSSSelector> deleteAnnotationCssSelectors(Integer bodyPK, Integer targetPK) {
        String sql = "SELECT * FROM annotation_css_selector_delete(?, ?)";
        Object[] params = {bodyPK, targetPK};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationCSSSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationDataPositionSelector createAnnotationDataPositionSelector(Integer bodyPK, Integer targetPK, String selectorIri, Integer start, Integer end, String selectorJson) {
        String sql = "SELECT * FROM annotation_data_position_selector_create(?, ?, ?, ?, ?, ?}";
        Object[] params = {bodyPK, targetPK, selectorIri, start, end, selectorJson};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationDataPositionSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationDataPositionSelector> deleteAnnotationDataPositionSelectors(Integer bodyPK, Integer targetPK) {
        String sql = "SELECT * FROM annotation_data_position_selector_delete(?, ?)";
        Object[] params = {bodyPK, targetPK};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationDataPositionSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationFragmentSelector createAnnotationFragmentSelector(Integer bodyPK, Integer targetPK, String selectorIri, String conformsTo, String value, Integer x, Integer y, Integer w, Integer h, Integer start, Integer end, String selectorJson) {
        String sql = "SELECT * FROM annotation_fragment_selector_create(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {bodyPK, targetPK, selectorIri, conformsTo, value, x, y, w, h, start, end, selectorJson};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationFragmentSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationFragmentSelector> deleteAnnotationFragmentSelectors(Integer bodyPK, Integer targetPK) {
        String sql = "SELECT * FROM annotation_fragment_selector_delete(?, ?)";
        Object[] params = {bodyPK, targetPK};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationFragmentSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationSVGSelector createAnnotationSvgSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson) {
        String sql = "SELECT * FROM annotation_svg_selector_create(?, ?, ?, ?, ?)";
        Object[] params = {bodyPK, targetPK, selectorIri, value, selectorJson};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationSVGSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationSVGSelector> deleteAnnotationSvgSelectors(Integer bodyPK, Integer targetPK) {
        String sql = "SELECT * FROM annotation_svg_selector_delete(?, ?)";
        Object[] params = {bodyPK, targetPK};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationSVGSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationTextPositionSelector createAnnotationTextPositionSelector(Integer bodyPK, Integer targetPK, String selectorIri, Integer start, Integer end, String selectorJson) {
        String sql = "SELECT * FROM annotation_text_position_selector_create(?, ?, ?, ?, ?, ?)";
        Object[] params = {bodyPK, targetPK, selectorIri, start, end, selectorJson};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationTextPositionSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationTextPositionSelector> deleteAnnotationTextPositionSelectors(Integer bodyPK, Integer targetPK) {
        String sql = "SELECT * FROM annotation_text_position_selector_delete(?, ?)";
        Object[] params = {bodyPK, targetPK};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationTextPositionSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationTextQuoteSelector createAnnotationTextQuoteSelector(Integer bodyPK, Integer targetPK, String selectorIri, String exact, String prefix, String suffix, String selectorJson) {
        String sql = "SELECT * FROM annotation_text_quote_selector_create(?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {bodyPK, targetPK, selectorIri, exact, prefix, suffix, selectorJson};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationTextQuoteSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationTextQuoteSelector> deleteAnnotationTextQuoteSelectors(Integer bodyPK, Integer targetPK) {
        String sql = "SELECT * FROM annotation_text_quote_selector_delete(?, ?)";
        Object[] params = {bodyPK, targetPK};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationTextQuoteSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationXPathSelector createAnnotationXPathSelector(Integer bodyPK, Integer targetPK, String selectorIri, String value, String selectorJson) {
        String sql = "SELECT * FROM annotation_xpath_selector_create(?, ?, ?, ?, ?)";
        Object[] params = {bodyPK, targetPK, selectorIri, value, selectorJson};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationXPathSelectorRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationXPathSelector> deleteAnnotationXPathSelectors(Integer bodyPK, Integer targetPK) {
        String sql = "SELECT * FROM annotation_xpath_selector_delete(?, ?)";
        Object[] params = {bodyPK, targetPK};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationXPathSelectorRowMapper());
    }
}
