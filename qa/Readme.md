**Requirements:**
1. Install Java8
2. Install Maven (for execution by command line)

**Running functional tests:**
1. Add the tag you want to run to your .feature file and run that tag
2. Specify the environment you want to run your test on
3. Example to run tests with `@mutation` tag: <br/>
`./run-test.sh @mutation`
4. Example to run all test except the `@mutation` tag (this is the default command): <br/>
`./run-test.sh ~@mutation`

Generated test report will be available on folder `target/cucumber-html-reports`

**Running performance tests:**
1. Run `./run-perf-test.sh`

Generated performance test report will be available on folder `target/gatling/loadtests-`
