package com.example.demo;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

import org.reactivestreams.FlowAdapters;

import reactor.core.publisher.Flux;

public class ReactiveApi2FlowApi {

        public static void main(String[] args) {

                Flux<String> publisher = Flux
                                .just("Red", "Blue", "Yellow", "Black");
                // Using Reactive Stream API
                publisher
                                .subscribe(item -> System.out
                                                .printf("I got %s\n", item),
                                                e -> System.out
                                                                .println("I got error."),
                                                () -> System.out
                                                                .println("I got completed."));

                // Using Flow API
                Flow.Publisher<String> flowPublisher = FlowAdapters
                                .toFlowPublisher(publisher);
                flowPublisher
                                .subscribe(new Flow.Subscriber<String>() {
                                        Subscription subscription;

                                        @Override
                                        public void onSubscribe(Subscription subscription) {
                                                this.subscription = subscription;
                                                subscription
                                                                .request(1);
                                        }

                                        @Override
                                        public void onNext(String item) {
                                                System.out
                                                                .printf("I got %s\n", item);
                                                subscription
                                                                .request(1);

                                        }

                                        @Override
                                        public void onError(Throwable throwable) {
                                                System.out
                                                                .println("I got error.");

                                        }

                                        @Override
                                        public void onComplete() {
                                                System.out
                                                                .println("I got completed.");

                                        }

                                });
        }

}