//package com.project.springbatch.step10.retry.template;
//
//
//import com.project.springbatch.step10.retry.api.RetryTestExcedption;
//import com.project.springbatch.web.model.CustomerVO;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.classify.BinaryExceptionClassifier;
//import org.springframework.classify.Classifier;
//import org.springframework.retry.RecoveryCallback;
//import org.springframework.retry.RetryCallback;
//import org.springframework.retry.RetryContext;
//import org.springframework.retry.support.DefaultRetryState;
//import org.springframework.retry.support.RetryTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RetryItemProcessor2 implements ItemProcessor<String, CustomerVO> {
//
//    @Autowired
//    private RetryTemplate retryTemplate;
//
//    private int cnt = 0;
//
//    @Override
//    public CustomerVO process(String item) throws Exception {
//
//        Classifier<Throwable, Boolean> classifier = new BinaryExceptionClassifier(true);
//
//
//        /**
//         * recover을 사용하면 exception이 몇번이던 상관없이 다시 처리한다.
//         */
//        CustomerVO customerVO= retryTemplate.execute(new RetryCallback<CustomerVO, RuntimeException>() {
//            @Override
//            public CustomerVO doWithRetry(RetryContext retryContext) throws RuntimeException {
//
//                /**
//                 * 여기서 재시도를 template의 따라서 2번을 수행한다. 만약에 2번이 다 끝나면 recover로 가게된다.
//                 * retry를 하게 되면 전 단계로 가서 받는건데 template을 사용하면 전 단계로 가지 않는다.
//                 */
//                if (item.equals("1")|| item.equals("2")|| item.equals("3")|| item.equals("4")){
//                    cnt ++;
//                    throw new RetryTestExcedption("process exception skip : "+ cnt);
//                }
//
//                return new CustomerVO(item, String.valueOf(cnt));
//            }
//        }, new RecoveryCallback<CustomerVO>() {
//            @Override
//            public CustomerVO recover(RetryContext retryContext) throws Exception {
//
//                System.out.println("===========================");
//                System.out.println("retryContext = " + retryContext);
//                System.out.println("cnt = " + cnt);
//                System.out.println("item = " + item);
//                System.out.println("===========================");
//                return new CustomerVO("999", String.valueOf(cnt));
//            }
//        });
//        return customerVO;
//    }
//}
//
//
///**
// * 이 현상의 이유를 단계별로 설명하겠습니다:
// * <p>
// * 청크 크기와 처리 과정:
// * <p>
// * 청크 크기는 5로 설정되어 있습니다.
// * 첫 번째 청크에서 0, 1은 정상 처리되고, 2, 3은 스킵됩니다.
// * 4가 처리된 후, Spring Batch는 이 청크를 완료하려고 합니다.
// * <p>
// * <p>
// * 청크 완료와 추가 읽기:
// * <p>
// * 그러나 이 시점에서 청크에는 3개의 아이템(0, 1, 4)만 있습니다.
// * Spring Batch는 청크를 채우기 위해 추가 아이템을 읽습니다.
// * 따라서 5번 아이템도 이 청크에 포함됩니다.
// * <p>
// * <p>
// * 구분선 출력 시점:
// * <p>
// * RetryItemProcessor에서 구분선(================)은 각 아이템 처리 후 출력됩니다.
// * 따라서 0, 1, 4, 5에 대해 각각 구분선이 출력됩니다.
// * <p>
// * <p>
// * 결과적인 출력:
// * <p>
// * 첫 번째 청크: 4개의 구분선 (0, 1, 4, 5에 대해)
// * 이후 청크들: 각각 5개의 구분선
// * 마지막 청크: 2개의 구분선 (29번 아이템까지)
// * <p>
// * <p>
// * <p>
// * 이로 인해 총 11개의 구분선 그룹이 출력됩니다 (4 + 5 + 2 = 11).
// */