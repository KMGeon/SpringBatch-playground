//package com.project.springbatch.step1;
//
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class Batch1 {
//
//    /**
//     * 1. @Bean 애노테이션을 사용하여 ApplicationRunner 타입의 빈을 정의하고 있습니다.
//     *
//     * 2. ApplicationRunner 인터페이스의 run 메서드를 오버라이드하여 구현하고 있습니다.
//     *
//     * 3. JobLauncher와 Job 객체를 주입받아 사용합니다.
//     *
//     * 4. run 메서드 내부에서 JobLauncher의 run 메서드를 호출하여 Job을 실행합니다.
//     *
//     * 5. JobParametersBuilder를 사용하여 JobParameters를 생성합니다.
//     *      addString 메서드를 사용하여 "UUID" 파라미터에 랜덤한 UUID 값을 할당합니다.
//     *      toJobParameters 메서드를 호출하여 JobParameters 객체를 얻습니다.
//     *
//     * 6. 생성된 JobParameters를 jobLauncher.run 메서드에 전달하여 Job을 실행합니다.
//     */

////
////    @Bean
////    Tasklet tasklet() {
////        return (contribution, chunkContext) -> {
////            System.out.println("Hello, World!");
////            return RepeatStatus.FINISHED;
////        };
////    }
////
////
////    @Bean
////    Job job(JobRepository jobRepository, Step step) {
////        return new JobBuilder("job", jobRepository)
////                .start(step)
////                .build();
////    }
////
////    @Bean
////    Step step1(JobRepository jobRepository, Tasklet tasklet, PlatformTransactionManager transactionManager) {
////        return new StepBuilder("step1", jobRepository)
////                .tasklet(tasklet, transactionManager)
////                .build();
////    }
//}
