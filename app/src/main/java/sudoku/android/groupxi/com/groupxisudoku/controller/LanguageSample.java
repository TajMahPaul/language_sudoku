package sudoku.android.groupxi.com.groupxisudoku.controller;

import java.io.Serializable;

class LanguageSample implements Serializable {
    private String lang_a;
    private String lang_b;


    public String getLang_a() {
        return lang_a;
    }

    public void setLang_a(String lang_a) {
        this.lang_a = lang_a;
    }

    public String getLang_b() {
        return lang_b;
    }

    public void setLang_b(String lang_b) {
        this.lang_b = lang_b;
    }
    @Override
    public String toString() {
        return "LanguageSample{" +
                "lang_a='" + lang_a + '\'' +
                ", lang_b='" + lang_b + '\'' +
                '}';
    }


}
