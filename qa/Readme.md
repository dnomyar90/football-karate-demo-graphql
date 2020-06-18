**Requirements:**
1. Install Java8
2. Install Maven (for execution by command line)

**How to run:**
1. Add the tag you want to run to your .feature file and run that tag
2. Specify the environment you want to run your test on
3. Example to run tests with `@smoke` tag on staging: <br/>
`mvn clean test -Dcucumber.options="--tags @smoke" -Dtest=TestRunner -Dkarate.env=staging`
4. Example to run all test except the `@ignore` tag on staging (this is the default command): <br/>
`mvn clean test -Dcucumber.options="--tags ~@ignore" -Dtest=TestRunner -Dkarate.env=staging`
