# Spring Batch 5

## 1. 자바 요구사항 17버전
- spring batch5로 변경되면서 spring famework 6에 필요한 java 17버전이 증가

<br/>
<br/>

## 2.  데이터 소스 및 트랜잭션 관리자 요구 사항 업데이트
https://docs.spring.io/spring-batch/docs/5.0.4/reference/html/whatsnew.html
1.  버전 4에서는 `MapJobRepositoryFactoryBean`, `MapJobExplorerFactoryBean` 을 통하여  메로리에서 맵 기반 작업 저장소를 제공했다. 하지만 버전 5로 변경되면서 모두 `deprecated`가 되면서 H2 등의 인메모리 디비를 이용하여 사용하게 만들었다.

<br/>
<br/>

## 3.  EnableBatchProcessing의 새로운 주석 속성
[https://github.com/spring-projects/spring-batch/issues/4232](https://github.com/spring-projects/spring-batch/issues/4232)
![[Pasted image 20240601222710.png]]


1. `@EnableBatchProcessing`을 사용할 필요가 없다.
    - 기존에 4버전에서는 `EnableBatchProcessing`을 통하여 다음 작업을 초기화 및 빈으로 등록된 모든 Job을 검색해서 Job을 수행하게 만들었다.
```text
# @EnableBatchProcessing 기능

1. EnableBatchProcessing

2. SimpleBatchConfiguration
    - JobBuilderFactory와 StepBuilderFactory 생성
    - 프록시 객체로 생성 -> Job, Step의 빈 생성 시점은 Job, Step이 실행되는 시점

3. BatchConfigurerConfiguration
    - BasicBatchConfigurer : 프록시 객체의 실제 대상 객체를 생성하는 설정 클래스
    - JpaBatchConfigurer : Jpa 관련 객체를 생성하는 설정 클래스
    
4. BatchAutoConfiguration
    - 스프링 배치가 초기화 될 때 자동으로 실행되는 설정 클래스
    - Job을 수행하는 JobLauncherApplicationRunner 빈을 생성한다.

----------------------------------------------------------------------
```

<br/>
<br/>

## BATCH_JOB_INSTANCE - JobKey
```json

- Spring Batch5로 넘어가면서 처음에 Job이 Complete가 되면 Job을 다시 실행하지 못한다.
	- 하지만 이전 버전에서는 Job_Key가 변경이 되면서 실행된다.

[
  {
    "JOB_INSTANCE_ID": 1,
    "VERSION": 0,
    "JOB_NAME": "helloWorldJob",
    "JOB_KEY": "853d3449e311f40366811cbefb3d93d7"
  },
  {
    "JOB_INSTANCE_ID": 2,
    "VERSION": 0,
    "JOB_NAME": "helloWorldJob",
    "JOB_KEY": "e070bff4379694c0210a51d9f6c6a564"
  }
]

=========### Spring Batch 5 ### ====================
[
  {
    "JOB_INSTANCE_ID": 1,
    "VERSION": 0,
    "JOB_NAME": "job",
    "JOB_KEY": "d41d8cd98f00b204e9800998ecf8427e"
  }
]

[
  {
    "JOB_EXECUTION_ID": 1,
    "VERSION": 2,
    "JOB_INSTANCE_ID": 1,
    "CREATE_TIME": "2024-06-01 21:08:48.746105",
    "START_TIME": "2024-06-01 21:08:48.770750",
    "END_TIME": "2024-06-01 21:08:48.843762",
    "STATUS": "COMPLETED",
    "EXIT_CODE": "COMPLETED",
    "EXIT_MESSAGE": "",
    "LAST_UPDATED": "2024-06-01 21:08:48.844141"
  },
  {
    "JOB_EXECUTION_ID": 2,
    "VERSION": 2,
    "JOB_INSTANCE_ID": 1,
    "CREATE_TIME": "2024-06-01 21:09:04.899552",
    "START_TIME": "2024-06-01 21:09:04.929872",
    "END_TIME": "2024-06-01 21:09:04.955680",
    "STATUS": "COMPLETED",
    "EXIT_CODE": "NOOP",
    "EXIT_MESSAGE": "All steps already completed or no steps configured for this job.",
    "LAST_UPDATED": "2024-06-01 21:09:04.956461"
  }
]
```

### 1. 배치 핵심 패턴
- Read : 데이터베이스, 파일, 큐에서 다량의 데이터 조회한다.
- Process : 특정 방법으로 데이터를 가공한다.
- Write : 데이터를 수정된 양식으로 다시 저장한다.

### 2. 배치 시나리오
1. 배치 프로세스를 주기적으로 커밋
2. 동시 다발적인 job의 배치 처리, 대용량 병렬 처리
3. 실패 후 수동, 또느 스케줄링에 의한 재시작
4. 의존관계가 있는 step 여러 개를 순차적으로 처리
5. 조건적 flow 구성을 통한 체계적이고 유연한 배치 모델 구성
6. 반복, 재시도, skip 처리

![[Pasted image 20240529214412.png]]


### 배치 어노테이션

`@EnableBatchProcessing`
- 내부적으로 4개의 설정 클래스를 실행을 시킨다.
    - 스프링 배치의 모든 초기화 및 실행 구성이 이루어진다.
- 스프링 부트 배치의 자동 설정 클래스가 실행하여 빈으로 등록된 Job을 검색 -> 초기화와 동시에 Job을 실행한다.


> 1. BatchAutoConfiguration
     > 	- 스프링 배치가 초기화 될 때 자동으로 실행되는 설정 클래스
> 	- Job을 수행하는 JobLauncherApplicationRunner 빈 생성
> 2. SimpleBatchConfiguration
     > 	1. JobBuilderFactory와 StepBuilderFactory 생성
> 	2. 스프링 배치의 주요 구성 요소 생성 -> 프록시 객체로 생성
> 3. BatchConfigurerConfiguration
     > 	1. BasicBatchConfigurer
             > 		1. 2에서 생성한 프록시 객체의 실제 대상 객체를 생성하는 설정 클래스
             > 		2. 빈으로 의존성 주입 받아서 주요 객체들을 참조해서 사용할 수 있다.
> 4. JpaBatchConfigurer
     > 	1. JPA 관련 객체를 생성하는 설정 클래스

![[Pasted image 20240530202029.png]]




## 스프링 배치 메타 데이터
- 스프링 배치의 실행 및 관리를 목적으로 여러 도메인들(job, step, jobparameters..)의 정보를 crud할 수 있도록 스키마를 제공한다.
- 과거, 현재의 실행에 대한 정보, 실행의 성공/실패 여부를 관리한다.
- DB와 연동할 경우 메타 테이블이 필수로 생성을 해야된다.

### 스키마 생성 설정
- 스키마 제공
    - /org/springframework/batch/core/schema.*.sql
- 자동 생성 - spring.batch.jdbc.initalize-schema설정
    - always
        - 스프립트 항상 실행
        - rdb 설정이 되어 있을 경우 내장 db보다 우선적으로 실행
    - embedded
        - 내장 db일 때만 실행되며 스키마가 자동 생성됨, 기본값
    - never
        - 스크립트 싱행 안함
        - 내장 db 일 경우 스크립트가 생성이 안되기 때문에 오류 발생
        - **운영에서 수동으로 스크립트 생성 후 설정하는 것을 권장**


![[Pasted image 20240530210737.png]]

## 잡 관련 테이블
### Batch-job-instance
-  job이 실행될 때 jobinstance 정보가 정보가 저장되며 job_name과 job_key를 키로 하여 하나의 데이터가 저장한다.
- 동일한 job_name, job_key로 중복 저장될 수 없다.

###  Batch-Job-Excution
- job의 실행정보가 저장되며  job life cycle, 시작, 종료 시간, 실행상태, 메시지를 관리한다.

###  Batch-Job-Excution-Context
- job과 함께 실행되는 jobparameter정보를 저장

###  Batch-Job-Excution-Params
- job의 실행동안 여러가지 상태정보, 공유 데이터를 직렬화해서 저장
- step  간 서로 공유 가능하다.
    - excute context를 저장한다. 서로 공유가 가능하다.

---
## 스탭 관련 테이블
### Batch-step-excution
- step의 실행정보가 저장되며 생성, 시작, 종료, 실행 상태, 메시지를 관리한다.

### Batch-step-excution-context
- step의 실행동안 여러가지 상태정보, 공유 데이터를 직렬화 해서 저장
- step 별로 저장되며 step간 서로 공유할 수 없다.


# Job
- 배치 계층 구조에서 가장 상위에 있는 개념  > 하나의 배치 자체를 의미한다.
- job Configuration을 통해 생성되는 객체 단위로서 배치작업을 어떻게 구성하고 실행할 것인지 전체적으로 설정하고 명시
-  job은 최소 1개 이상의  Step을 포함하고 있어야 한다.


### 기본 구현체
1. SimpleJob
    - 순차적으로 스탭을 실행하는 잡

2. FlowJob
    - 특정 조건과 흐름에 따라 스탭을 구성하여 실행하는 잡
    - Flow 객체를 실행시켜 작업을 진행

![[Pasted image 20240601113523.png]]

- 잡런쳐는 잡파라미터, 잡을 매개변수로 받아서 실행한다.
    - 잡이 실행되면 step을 순차적으로 실행한다.


# JobInstance

1. 잡이 실행될 때 생성되는 **잡의 논리적 실행 단위 객체**로서 고유하게 식별 가능한 작업 실행
    1. 만약에 동일잡 잡을 오늘, 내일 실행되면 Job은 동일하지만 Job Instance는 다르다.
2. 처음 시작하는 경우 > Job + Jobparameter로 이루어진 JobInstance 생성
3. 이전과 동일한 Job 실행 > 이전에 만들었던 JobInstance를 리턴한다.
    1. 내부적으로 JobName + Jobkey ( jobparameters 의 해시 값)를 가지고 JobInstance를 얻음

**즉 Job, JobInstance는 1:N 관계를 가진다.**


### Batch_JOB_Instance 테이블과 매핑
-  Job-name, job_key가 동일한 데이터는 중복하여 저장할 수 없다.
```java
@Bean  
ApplicationRunner runner(JobLauncher jobLauncher, Job job) {  
    return new ApplicationRunner() {  
        @Override  
        public void run(ApplicationArguments args) throws Exception {  
            jobLauncher.run(job, new JobParametersBuilder()  
                    .addString("UUID", UUID.randomUUID().toString())  
                    .toJobParameters());  
        }  
    };  
}
```

![[Pasted image 20240601125259.png]]


1. 잡 런처 : batch job을 실행한다.
    1. 필요한 파라미터 ( job, jobparameters )

2. run (job, jobparameters)

3. jobrepository
    - 잡 실행정보 (생성, 파라미터)의 메타 데이터를 저장하는 역활을 수행한다.
        - job, jobparameter를 통하여 db에 접근하여 이미 생성된 잡 인스턴스가 있는지 확인한다.
        - 만약에 존재하면 jobinstance를 생성하고 없다면 기존의 jobInstance를 리턴한다.



## JobParameter
---
- 정의
    - Job을 실행할 때 함께 포함되어 사용되는 파라미터
    - 하나의 job에 존재할 수 있는 jobInstance를 구분하기 위한 용도
    - JobParameters와 JobInstance는 1:1 관계를 가진다.


- 샌성 및 바인딩
    - 어플리케이션 실행 시 주입
        - java -jar xxx.jar `requestDate=20240101`
    - 코드로 생성
        - JobParameterBuilder, DefaultJobParametersConverter
    - SpEL
        - @Value("#jobparameter{requestDate}"), @JobScope, @StepScope 선언 필수
            - java -jar xxx.jar `requestDate=20240101` 외부에서 선언한 변수의 값을 선언

- BATCH_JOB_EXECUTION_PARAM 테이블 매핑
    - JOB_EXCUTION과 1:M관계


![[Pasted image 20240601191038.png]]

> JobParameter 타입은 `string, Date, Long, Double`을 지원한다.
> -> SpringBatch5 부터 LocalDate, LocalDateTime, LocalTime도 가능하다.
> 	-> Batch_job_excution_param

```java
@Component  
public class JobParameterTest implements ApplicationRunner{  
  
    @Autowired  
    private JobLauncher jobLauncher;  
  
    @Autowired  
    private Job job;  
  
    @Override  
    public void run(ApplicationArguments args) throws Exception {  
        JobParameters params = new JobParametersBuilder()  
                .addString("UUID", UUID.randomUUID().toString())  
                .addString("string", "name1")  
                .addLocalDate("localdate", LocalDate.now())  
                .addDate("date", new Date())  
                .addLocalDateTime("LocalDateTime", LocalDateTime.now())  
                .addDouble("double", 1.5)  
                .toJobParameters();  
  
        jobLauncher.run(job, params);  
    }  
}


@Bean  
Tasklet tasklet2() {  
    return (contribution, chunkContext) -> {  
  
        //linkedHashMap  
        // jobparameters 객체 안에서 데이터가 저장이 되어져 있다.        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();  
        System.out.println("UUID = " + jobParameters.getString("UUID"));  
        System.out.println("string = " + jobParameters.getString("string"));  
        System.out.println("localdate = " + jobParameters.getLocalDate("localdate"));  
        System.out.println("date = " + jobParameters.getDate("date"));  
        System.out.println("localtime = " + jobParameters.getLocalDateTime("LocalDateTime"));  
        System.out.println("double = " + jobParameters.getDouble("double"));  
  
  
        //map  
        // map에 바로 저장한 값을 주기 때문에 jobparameters 객체를 통해서 데이터를 가져올 필요가 없다.        // jobparameters의 객체를 변경을 하게 된다면 그 시점의 값만 확인할 수 있다.        //chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters();        Map<String, Object> chunck = chunkContext.getStepContext().getJobParameters();  
        System.out.println("UUID = " + chunck.get("UUID"));  
        System.out.println("string = " + chunck.get("string"));  
        System.out.println("localdate = " + chunck.get("localdate"));  
        System.out.println("date = " + chunck.get("date"));  
        System.out.println("localtime = " + chunck.get("LocalDateTime"));  
        System.out.println("double = " + chunck.get("double"));  
  
  
        System.out.println("step2 was excuted!");  
        return RepeatStatus.FINISHED;  
    };  
}
```


# JobExcution
---
> 정의
- JobInstance에 대해 한번의 시도를 의미하는 객체로 Job 실행 중에 발생한 정보를 저장하고 있는 객체
- `시작, 종료시간, 상태(시작, 완료, 실패), 종료상태`의 속성을 가진다.

>JobInstance와 관계
>	- Completed, Failed의 상태를 가진다.
       >		- Completed일 경우에는 JobInstance 실행이 완료된 것으로 간주하여 재 실행이 불가능
                  >		- Failed이면 완료되지 않은 상태라고 간주하여 재실행이 가능하다. (JobParameter가 동일하여도 )
                             `JobExcution`의 실행 상태가 `Completed`가 될 때까지 하나의 JobInstance내에서 Retry가 생길 수 있다.

- JobInstance와 JobExecution은 1:N 관계를 가진다.
- ![[Pasted image 20240601204141.png]]
  ![[Pasted image 20240601204259.png]]
  ![[Pasted image 20240601204513.png]]





# Step
- `Batch job을 구성하는 독립적인 단계`, 실제 배치 처리를 정의하고 컨트롤 하는데 필요한 모든 정보를 가지고 있는 도메인 객체
- 단순한 태스크 뿐 아니라 입, 출력과 관련된 비즈니스 로직이 포함된 설정

#### 기본 구현체
1. TaskletStep
    - 가장 기본이 되는 클래스로서 Tasklet 타입의 구현체들을 제어한다.

2. PartitionStep
    - 멀티 스레드 방식으로 Step을 여러 개로 분리해서 실행한다.

3. JobStep
    - Step 내에서 Job을 실행한다.

4. FlowStep
    - Step 내에서 Flow를 실행하도록 한다.

![[Pasted image 20240601225959.png]]
![[Pasted image 20240601230113.png]]

