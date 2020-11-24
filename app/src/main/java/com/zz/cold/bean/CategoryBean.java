package com.zz.cold.bean;

import java.util.List;

public class CategoryBean extends DictBean {

    List<Child1> childs;

    public List<Child1> getChilds() {
        return childs;
    }

    public class Child1 extends DictBean {
        List<Child2> childs;

        public List<Child2> getChilds() {
            return childs;
        }
    }
    public class Child2 extends DictBean {

    }

}
