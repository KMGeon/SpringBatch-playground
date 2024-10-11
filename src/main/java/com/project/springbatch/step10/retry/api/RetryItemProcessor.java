package com.project.springbatch.step10.retry.api;


import org.springframework.batch.item.ItemProcessor;

public class RetryItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        if (item.equals("2")||item.equals("3")){
            cnt++;
            throw new RetryTestExcedption("RetryException : "+ cnt);
        }
        System.out.println("================");
        return item;
    }
}


/**
 * 이 현상의 이유를 단계별로 설명하겠습니다:
 *
 * 청크 크기와 처리 과정:
 *
 * 청크 크기는 5로 설정되어 있습니다.
 * 첫 번째 청크에서 0, 1은 정상 처리되고, 2, 3은 스킵됩니다.
 * 4가 처리된 후, Spring Batch는 이 청크를 완료하려고 합니다.
 *
 *
 * 청크 완료와 추가 읽기:
 *
 * 그러나 이 시점에서 청크에는 3개의 아이템(0, 1, 4)만 있습니다.
 * Spring Batch는 청크를 채우기 위해 추가 아이템을 읽습니다.
 * 따라서 5번 아이템도 이 청크에 포함됩니다.
 *
 *
 * 구분선 출력 시점:
 *
 * RetryItemProcessor에서 구분선(================)은 각 아이템 처리 후 출력됩니다.
 * 따라서 0, 1, 4, 5에 대해 각각 구분선이 출력됩니다.
 *
 *
 * 결과적인 출력:
 *
 * 첫 번째 청크: 4개의 구분선 (0, 1, 4, 5에 대해)
 * 이후 청크들: 각각 5개의 구분선
 * 마지막 청크: 2개의 구분선 (29번 아이템까지)
 *
 *
 *
 * 이로 인해 총 11개의 구분선 그룹이 출력됩니다 (4 + 5 + 2 = 11).
 */