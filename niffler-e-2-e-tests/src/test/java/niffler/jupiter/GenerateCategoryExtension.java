package niffler.jupiter;

import niffler.api.dto.CategoryJson;
import org.junit.jupiter.api.extension.*;

import static niffler.api.methods.PostCategory.postCategory;

public class GenerateCategoryExtension implements ParameterResolver, BeforeEachCallback {
    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(GenerateCategoryExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        GenerateSpend annotationCategory = context.getRequiredTestMethod().getAnnotation(GenerateSpend.class);
        if (annotationCategory!=null){
            CategoryJson category = new CategoryJson();
            category.setCategory(annotationCategory.category());
            category.setUsername(annotationCategory.username());
            postCategory(category);
            context.getStore(NAMESPACE).put("category", category);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get("category", CategoryJson.class);
    }
}
