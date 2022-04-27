package com.ru.mirea.kalugina.mireaproject.ui.history;

public class History {
    private final String title;
    private final String content;

    History(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
