## Section 4: Spring profile 

During development process, we need to switch between multiple environment like local, dev, test, prod. Each env have their own configuration value. 

Need to have a way to switch profile easily.

Should move profile into subFolder. In future, there will be many configuration/folder in resources. 

Task: 
- Read about Profile and why we need them. 
- config profile for local and dev. At now, have 1 field "test.profile.message" in both file. its value is different for each profile 
- Create an endpoint and return "test.profile.message". Make sure the response is different if we switch profile. 
- When switch profile, check log, and will see line "The following 1 profile is active: "dev"". 
- Need to know how to set profile via VM options
- Need to know to read field in configuration file.


Ref:
- Spring Boot Profiles - https://zetcode.com/springboot/profile/#?utm_content=cmp-true
- Organizing Spring Boot profiles into directories - https://stackoverflow.com/questions/64557916/organizing-spring-boot-profiles-into-directories