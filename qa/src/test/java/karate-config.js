function fn() {
  karate.configure('connectTimeout', 120000);
  karate.configure('readTimeout', 120000);
  karate.configure('report', { showLog: false, showAllSteps: false });

  // Faker for randomized data
  var faker = Java.type('FakerHelper');

  // Default value for URL config
  var config = {
    baseUrl: 'http://localhost:4000/graphql',
  };
  config.faker = faker;
  return config;
}