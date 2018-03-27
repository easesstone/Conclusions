package com.sydney.dream.drill;


import io.netty.buffer.DrillBuf;
import org.apache.drill.exec.expr.DrillSimpleFunc;
import org.apache.drill.exec.expr.annotations.FunctionTemplate;
import org.apache.drill.exec.expr.annotations.Output;
import org.apache.drill.exec.expr.annotations.Param;
import org.apache.drill.exec.expr.holders.*;

import javax.inject.Inject;


/**
 *  请不要忘记了implements 操作
 */
@FunctionTemplate(
        name = "facecomp",
        scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL
)
public class FaceComp implements DrillSimpleFunc {
    @Param
    NullableVarCharHolder input;

    // 传入的feature，整数值
    @Param(constant = true)
    VarCharHolder mask;

    @Output
    Float8Holder out;

    @Inject
    DrillBuf buffer;

    public void setup() {

    }

    public void eval() {
        // get the value and replace with
        String maskValue = org.apache.drill.exec.expr.fn.impl.StringFunctionHelpers.getStringFromVarCharHolder(mask);
        String stringValue = org.apache.drill.exec.expr.fn.impl.StringFunctionHelpers.toStringFromUTF8(input.start, input.end, input.buffer);

        // build the mask substring
        // 必须是全类名
        double repeated = new com.sydney.dream.drill.FaceCompareFunction().featureCompare(maskValue, stringValue);

        // put the output value in the out buffer
        out.value = repeated;

        buffer.setDouble(0, repeated);

    }
}
