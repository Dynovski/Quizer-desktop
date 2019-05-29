package proz.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proz.database.daos.CategoryDao;
import proz.database.models.Category;
import proz.utils.converters.CategoryConverter;
import proz.utils.exceptions.ApplicationException;
import java.util.List;

public class CategoryDataModel
{
    private static ObservableList<CategoryFxModel> categories = FXCollections.observableArrayList();
    private static ObjectProperty<CategoryFxModel> category = new SimpleObjectProperty<>();
    private static CategoryDao categoryDao = new CategoryDao();

    private CategoryDataModel() {}

    private static void populateCategories(List<Category> categoryList)
    {
        categories.clear();
        categoryList.forEach(category -> {
            CategoryFxModel categoryFx = CategoryConverter.categoryToCategoryFx(category);
            categories.add(categoryFx);
        });
    }
    public static void fetchCategoriesFromDataBase() throws ApplicationException
    {
        List<Category> categories = categoryDao.queryForAll(Category.class);
        populateCategories(categories);
    }

    public static void deleteCategoryById(int categoryId) throws ApplicationException
    {
        TestDataModel.deleteTestsFromCategory(categoryId);
        categoryDao.deleteById(Category.class, categoryId);
        fetchCategoriesFromDataBase();
    }

    public static void saveCategoryInDataBase(String categoryName) throws ApplicationException
    {
        Category newCategory = new Category(categoryName);
        categoryDao.createOrUpdateAndRefresh(newCategory);
        categories.add(CategoryConverter.categoryToCategoryFx(newCategory));
    }

    public static void updateCategoryInDataBase() throws ApplicationException
    {
        categoryDao.createOrUpdate(CategoryConverter.categoryFxToCategory(getCategory()));
    }

    public static ObservableList<CategoryFxModel> getCategories()
    {
        return categories;
    }

    public static void setCategories(ObservableList<CategoryFxModel> categories)
    {
        CategoryDataModel.categories = categories;
    }

    public static CategoryFxModel getCategory()
    {
        return category.get();
    }

    public static ObjectProperty<CategoryFxModel> categoryProperty()
    {
        return category;
    }

    public static void setCategory(CategoryFxModel category)
    {
        CategoryDataModel.category.set(category);
    }

    public static CategoryDao getCategoryDao()
    {
        return categoryDao;
    }
}
