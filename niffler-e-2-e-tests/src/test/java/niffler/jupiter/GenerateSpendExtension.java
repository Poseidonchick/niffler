package niffler.jupiter;

import niffler.api.dto.SpendJson;
import org.junit.jupiter.api.extension.*;

import java.util.Date;

import static niffler.api.methods.PostAddSpend.postAddSpend;

public class GenerateSpendExtension implements ParameterResolver, BeforeEachCallback {
    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(GenerateSpendExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        GenerateSpend annotationSpend = context.getRequiredTestMethod().getAnnotation(GenerateSpend.class);
        if (annotationSpend != null) {
            SpendJson spend = new SpendJson();
            spend.setUsername(annotationSpend.username());
            spend.setAmount(annotationSpend.amount());
            spend.setCategory(annotationSpend.category());
            spend.setCurrency(annotationSpend.currencyValues());
            spend.setDescription(annotationSpend.description());
            spend.setSpendDate(new Date());
            postAddSpend(spend);
            context.getStore(NAMESPACE).put("spend", spend);

        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(SpendJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get("spend", SpendJson.class);
    }
}
