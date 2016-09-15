/**
 */
'use strict';

angular.module('myApp.ws', ['ngWebSocket'])


    .controller('tweetsCtrl', ['$scope', function ($scope) {

        $scope.goToAuthPage = function(){
            window.open($scope.auth.url);
        };

        function refreshUI() {
            $scope.apply();
        }

        $scope.getTweets = function () {

            var socket = new SockJS('http://localhost:8088/report');
            var stompClient = Stomp.over(socket);

            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);

                var id = stompClient.subscribe('/topic/greetings', function(rep){
                    var report = JSON.parse(rep.body);
                    if(report.status == "OK") {

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

                        $scope.result = report.result;
                        $scope.positive = report.positive;
                        $scope.negative = report.negative;
                        $scope.neutral = report.neutral;
                        $scope.total = report.total;
                        $scope.esNegative = report.negExample;
                        $scope.esPositive = report.posExample;

                        $scope.$digest();


                        document.getElementById("report").style.visibility = "visible";

                    } else {
                        alert(report.message);
                    }
                });
                
                stompClient.begin("tx");
                var paramsMessage = {};
                paramsMessage.author = $scope.author;
                paramsMessage.tag = $scope.tag;
                paramsMessage.words = $scope.words;
                paramsMessage.notwords = $scope.notwords;
                paramsMessage.lang = $scope.lang;
                paramsMessage.sinceDate = $scope.sinceDate;
                paramsMessage.toDate = $scope.toDate;
                paramsMessage.noURL = $scope.noURL.value;
                paramsMessage.noMedia = $scope.noMedia.value;
                stompClient.send("/app/hello", {}, JSON.stringify(paramsMessage));
                
                stompClient.commit("tx");
                

                
            });


        };

        $scope.test = function () {
            var socket = new SockJS('http://localhost:8088/report');
            var stompClient = Stomp.over(socket);

            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);

                var id = stompClient.subscribe('/topic/test', function(rep){
                    var report = JSON.parse(rep);
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
                });

                stompClient.begin("tx");
                
                stompClient.send("/app/ciao");

                stompClient.commit("tx");



            });

        };

        $scope.noURL = {
            value : false
        };

        $scope.noMedia = {
            value : false
        };
    }]);