package com.digirati.elucidate.infrastructure.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.digirati.elucidate.model.annotation.selector.fragment.TFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.XYWHFragmentSelector;

public class SelectorUtils {

    private static final Pattern XYWH_MATCHER = Pattern.compile("xywh=(\\d+),(\\d+),(\\d+),(\\d+)");
    private static final Pattern T_MATCHER = Pattern.compile("t\\=(\\d+)?(,)?(\\d+)?");

    public static XYWHFragmentSelector extractXywhSelector(String str) {

        if (StringUtils.isNotBlank(str)) {

            Matcher matcher = XYWH_MATCHER.matcher(str);
            if (matcher.find()) {

                String xStr = matcher.group(1);
                String yStr = matcher.group(2);
                String wStr = matcher.group(3);
                String hStr = matcher.group(4);

                if (NumberUtils.isDigits(xStr) && NumberUtils.isDigits(yStr) && NumberUtils.isDigits(wStr) && NumberUtils.isDigits(hStr)) {

                    XYWHFragmentSelector xywhFragmentSelector = new XYWHFragmentSelector();
                    xywhFragmentSelector.setX(Integer.parseInt(xStr));
                    xywhFragmentSelector.setY(Integer.parseInt(yStr));
                    xywhFragmentSelector.setW(Integer.parseInt(wStr));
                    xywhFragmentSelector.setH(Integer.parseInt(hStr));
                    return xywhFragmentSelector;
                }
            }

        }
        return null;
    }

    public static TFragmentSelector extractTSelector(String str) {

        if (StringUtils.isNotBlank(str)) {

            Matcher matcher = T_MATCHER.matcher(str);
            if (matcher.find()) {

                String startStr = matcher.group(1);
                String endStr = matcher.group(3);

                if (StringUtils.isBlank(startStr) && StringUtils.isNotBlank(endStr)) {
                    startStr = Integer.toString(0);
                } else if (StringUtils.isNotBlank(startStr) && StringUtils.isBlank(endStr)) {
                    endStr = Integer.toString(Integer.MAX_VALUE);
                }

                if (NumberUtils.isDigits(startStr) && NumberUtils.isDigits(endStr)) {

                    TFragmentSelector tFragmentSelector = new TFragmentSelector();
                    tFragmentSelector.setStart(Integer.parseInt(startStr));
                    tFragmentSelector.setEnd(Integer.parseInt(endStr));
                    return tFragmentSelector;
                }
            }
        }
        return null;
    }
}
