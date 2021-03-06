/**
 */
'use strict';

angular.module('myApp.twitter', ['ngResource'])

    .factory('Report', ['$resource', function ($resource) {
        return $resource('http://127.0.0.1:8088/report')
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

                if(report.status == "OK") {
                    $scope.result = report.result;
                    $scope.positive = report.positive;
                    $scope.negative = report.negative;
                    $scope.neutral = report.neutral;
                    $scope.total = report.total;
                    $scope.esNegative = report.negExample;
                    $scope.esPositive = report.posExample;

                    var chart1 = c3.generate({
                        bindto: "#graph",
                        data: {
                            columns: [
                                ["Positivi", report.positive],
                                ["Negativi", report.negative],
                                ["Neutri", report.neutral]
                            ],
                            type: 'pie',
                            colors: {
                                Positivi: '#26A65B',
                                Negativi: '#D91E18',
                                Neutri: '#FDD835'
                            }
                        },
                    });
                    document.getElementById("report").style.visibility = "visible";
                } else {
                    alert(report.message);
                }
                

            })
        };

        $scope.test = function () {
            Test.get(function (report) {
                if(report.status == "OK") {
                    $scope.result = report.result;
                    $scope.positive = report.positive;
                    $scope.negative = report.negative;
                    $scope.neutral = report.neutral;
                    $scope.total = report.total;
                    $scope.esNegative = report.negExample;
                    $scope.esPositive = report.posExample;

                    var chart1 = c3.generate({
                        bindto: "#graph",
                        data: {
                            columns: [
                                ["Positivi", report.positive],
                                ["Negativi", report.negative],
                                ["Neutri", report.neutral]

                            ],
                            type: 'pie',
                            colors: {
                                Positivi: '#26A65B',
                                Negativi: '#D91E18',
                                Neutri: '#FDD835',
                            }
                        },
                    });
                    document.getElementById("report").style.visibility = "visible";
                } else {
                    alert(report.message);
                }               

            });
           
        };

        $scope.noURL = {
            value : false
        };

        $scope.noMedia = {
            value : false
        }
    }]);