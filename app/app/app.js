'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'ngWebSocket',
    'ngSanitize',  
    'myApp.ws'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');

  $routeProvider
      .when("/", {templateUrl: "src/home.html"})
      .otherwise({redirectTo: '/'});
}]);
