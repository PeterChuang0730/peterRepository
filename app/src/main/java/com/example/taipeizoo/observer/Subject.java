package com.example.taipeizoo.observer;

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);

    void removeObserver(RepositoryObserver repositoryObserver);

    void notifyObservers();
}
