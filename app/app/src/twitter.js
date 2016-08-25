/**
 * Created by albertogiunta on 27/06/16
 */
'use strict';

angular.module('myApp.twitter', ['ngResource'])

    .factory('Report', ['$resource', function ($resource) {
        return $resource('http://127.0.0.1:8088/add')
    }])

    .factory('Test', ['$resource', function ($resource) {
        return $resource('http://127.0.0.1:8088/test')
    }])

    .controller('tweetsCtrl', ['$scope', 'Report', 'Test', function ($scope, Report, Test) {

        $scope.goToAuthPage = function(){
            window.open($scope.auth.url);
        };
        

        $scope.getTweets = function () {
            Report.get({author : $scope.author, tag : $scope.tag, words : $scope.words, notwords : $scope.notwords, lang : $scope.lang,
                sinceDate : $scope.sinceDate, toDate : $scope.toDate, noURL : $scope.noURL.value, noMedia : $scope.noMedia.value}, function (report) {

                $scope.result = report.result;
                $score.confidence = report.confidence;
            })
        };

        $scope.test = function () {
            Test.get();
        };

        $scope.noURL = {
            value : false
        };

        $scope.noMedia = {
            value : false
        }
    }]);