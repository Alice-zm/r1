package tmall.util;

import tmall.bean.Product;

import java.util.Comparator;

//综合排序比较器
public class ProductAllComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount()*o2.getSaleCount()-o1.getSaleCount()*o1.getReviewCount();
    }
}
