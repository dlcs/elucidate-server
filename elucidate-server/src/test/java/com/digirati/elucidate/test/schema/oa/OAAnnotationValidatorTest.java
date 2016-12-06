package com.digirati.elucidate.test.schema.oa;

import org.junit.Test;

import com.digirati.elucidate.test.schema.AbstractSchemaValidatorTest;

public class OAAnnotationValidatorTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "/schema/oa-annotation-schema.json";
    }

    @Test
    public void validateSimple() throws Exception {
        validateJson("/example-oa-annotation/simple.jsonld");
    }

    @Test
    public void validateFigureTwoOne() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.jsonld");
    }

    @Test
    public void validateFigureTwoOneOne() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.1.jsonld");
    }

    @Test
    public void validateFigureTwoOneTwo() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.2.jsonld");
    }

    @Test
    public void validateFigureTwoOneThreeOne() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.3.1.jsonld");
    }

    @Test
    public void validateFigureTwoOneThreeTwo() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.3.2.jsonld");
    }

    @Test
    public void validateFigureTwoOneThreeThree() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.3.3.jsonld");
    }

    @Test
    public void validateFigureTwoOneFour() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.4.jsonld");
    }

    @Test
    public void validateFigureTwoOneFive() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.5.jsonld");
    }

    @Test
    public void validateFigureTwoOneSix() throws Exception {
        validateJson("/example-oa-annotation/figure-2.1.6.jsonld");
    }

    @Test
    public void validateFigureTwoTwo() throws Exception {
        validateJson("/example-oa-annotation/figure-2.2.jsonld");
    }

    @Test
    public void validateFigureTwoTwoOne() throws Exception {
        validateJson("/example-oa-annotation/figure-2.2.1.jsonld");
    }

    @Test
    public void validateFigureThreeOneTwo() throws Exception {
        validateJson("/example-oa-annotation/figure-3.1.2.jsonld");
    }

    @Test
    public void validateFigureThreeTwoOne() throws Exception {
        validateJson("/example-oa-annotation/figure-3.2.1.jsonld");
    }

    @Test
    public void validateFigureThreeTwoTwoOne() throws Exception {
        validateJson("/example-oa-annotation/figure-3.2.2.1.jsonld");
    }

    @Test
    public void validateFigureThreeTwoTwoTwo() throws Exception {
        validateJson("/example-oa-annotation/figure-3.2.2.2.jsonld");
    }

    @Test
    public void validateFigureThreeTwoTwoThree() throws Exception {
        validateJson("/example-oa-annotation/figure-3.2.2.3.jsonld");
    }

    @Test
    public void validateFigureThreeTwoThreeOne() throws Exception {
        validateJson("/example-oa-annotation/figure-3.2.3.1.jsonld");
    }

    @Test
    public void validateFigureThreeThreeOne() throws Exception {
        validateJson("/example-oa-annotation/figure-3.3.1.jsonld");
    }

    @Test
    public void validateFigureThreeThreeTwo() throws Exception {
        validateJson("/example-oa-annotation/figure-3.3.2.jsonld");
    }

    @Test
    public void validateFigureThreeFourOne() throws Exception {
        validateJson("/example-oa-annotation/figure-3.4.1.jsonld");
    }

    @Test
    public void validateFigureThreeFive() throws Exception {
        validateJson("/example-oa-annotation/figure-3.5.jsonld");
    }

    @Test
    public void validateFigureFourOne() throws Exception {
        validateJson("/example-oa-annotation/figure-4.1.jsonld");
    }

    @Test
    public void validateFigureFourTwo() throws Exception {
        validateJson("/example-oa-annotation/figure-4.2.jsonld");
    }

    @Test
    public void validateFigureFourThree() throws Exception {
        validateJson("/example-oa-annotation/figure-4.3.jsonld");
    }
}
