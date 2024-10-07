### Classifier 
- 분류하다 의미를 가진다.
- 라우팅 패턴을 구현해서 ItemProcessor 구현체 중에서 하나를 호출하는 역활을 한다.

- if : 만약에 itemProcessor 가 1,2,3이 있다고 가정하면 if문 처럼 각각의 상황에 따라서 ItemProcessor를 특정해서 호출하는 것을 의미한다.

```java
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.classify.Classifier;

public ItemProcessor itemProcessor() {
    return new ClassifierCompositeItemProcessorBuilder<>()
            .classifier(Classifer) // 분류자 설정
            .build();
}

Classifier<C, T>
- C : Classifier타입
- T : 리턴하는 값
```