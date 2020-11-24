package com.zz.cold.bean;

import java.util.List;

public class CategoryBean extends DictBean {

    List<Child1> goodsTypeList;

    public List<Child1> getChilds() {
        return goodsTypeList;
    }

    public class Child1 extends DictBean {
        List<Child2> goodsTypeList;

        public List<Child2> getChilds() {
            return goodsTypeList;
        }
    }
    public class Child2 extends DictBean {

    }

}
