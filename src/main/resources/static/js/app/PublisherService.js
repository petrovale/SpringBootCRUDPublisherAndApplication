'use strict';

angular.module('crudApp').factory('PublisherService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllPublishers: loadAllPublishers,
                getAllPublishers: getAllPublishers,
                getPublisher: getPublisher,
                createPublisher: createPublisher,
                updatePublisher: updatePublisher,
                removePublisher: removePublisher
            };

            return factory;

            function loadAllPublishers() {
                console.log('Fetching all publishers');
                var deferred = $q.defer();
                $http.get(urls.PUBLISHER_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all publishers');
                            $localStorage.publishers = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading publishers');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllPublishers(){
                return $localStorage.publishers;
            }

            function getPublisher(id) {
                console.log('Fetching Publisher with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.PUBLISHER_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Publisher with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Publisher with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createPublisher(publisher) {
                console.log('Creating Publisher');
                var deferred = $q.defer();
                $http.post(urls.PUBLISHER_SERVICE_API, publisher)
                    .then(
                        function (response) {
                            loadAllPublishers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Publisher : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updatePublisher(publisher, id) {
                console.log('Updating Publisher with id '+id);
                var deferred = $q.defer();
                $http.put(urls.PUBLISHER_SERVICE_API + id, publisher)
                    .then(
                        function (response) {
                            loadAllPublishers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Publisher with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removePublisher(id) {
                console.log('Removing publisher with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.PUBLISHER_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllPublishers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Publisher with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);