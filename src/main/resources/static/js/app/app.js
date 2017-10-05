var app = angular.module('crudApp',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/SpringBootCRUDApp',
    PUBLISHER_SERVICE_API : 'http://localhost:8080/SpringBootCRUDApp/api/publisher/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'PublisherController',
                controllerAs:'ctrl',
                resolve: {
                    publishers: function ($q, PublisherService) {
                        console.log('Load all publishers');
                        var deferred = $q.defer();
                        PublisherService.loadAllPublishers().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

