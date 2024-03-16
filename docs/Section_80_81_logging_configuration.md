## Section 80_81: Logging Configuration
- Config rolling log:
  - Create a new log file when file size exceeds 10m
  - Only retain the last 5 files
- Add correlationId or tracerId to request, so that we can use correlationId to get all relevant log 
  - Handle cases that need to log for multithread 

**Solution**
### Rolling log configuration

https://www.codejava.net/frameworks/spring-boot/logback-rolling-files-example#
https://docs.spring.io/spring-boot/docs/2.7.15/reference/htmlsingle/#features.logging
```properties
# name pattern. must have %i 
logging.logback.rollingpolicy.file-name-pattern=${LOG_FILE}.%d{yyyy-MM-dd}.%i
# A new log file will be rolled out if size of the current log file exceeds this number.
logging.logback.rollingpolicy.max-file-size=1MB
# maximum number of days that the archive log files are kept
logging.logback.rollingpolicy.max-history=2
# Logback will delete the oldest log files in order to keep the total size doesn’t exceed the specified value.
logging.logback.rollingpolicy.total-size-cap=10MB
```

### CorrelationId 
- Using OncePerRequestFilter to adding correlationId to MDC
- Adding correlationId in to the log format
- Spring use LogBack as default and won't support InheritableThreadLocal. So, if we create child thread from current thread, MDC can't pass the correlationId variable. Need to set explicitly 





