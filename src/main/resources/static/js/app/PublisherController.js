'use strict';

angular.module('crudApp').controller('PublisherController',
    ['PublisherService', '$scope',  function( PublisherService, $scope) {

        var self = this;
        self.publisher = {};
        self.publishers=[];

        self.submit = submit;
        self.getAllPublishers = getAllPublishers;
        self.createPublisher = createPublisher;
        self.updatePublisher = updatePublisher;
        self.removePublisher = removePublisher;
        self.editPublisher = editPublisher;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.publisher.id === undefined || self.publisher.id === null) {
                console.log('Saving New Publisher', self.publisher);
                createPublisher(self.publisher);
            } else {
                updatePublisher(self.publisher, self.publisher.id);
                console.log('Publisher updated with id ', self.publisher.id);
            }
        }

        function createPublisher(publisher) {
            console.log('About to create publisher');
            PublisherService.createPublisher(publisher)
                .then(
                    function (response) {
                        console.log('Publisher created successfully');
                        self.successMessage = 'Publisher created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.publisher={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Publisher');
                        self.errorMessage = 'Error while creating Publisher: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updatePublisher(publisher, id){
            console.log('About to update publisher');
            PublisherService.updatePublisher(publisher, id)
                .then(
                    function (response){
                        console.log('Publisher updated successfully');
                        self.successMessage='Publisher updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Publisher');
                        self.errorMessage='Error while updating Publisher '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removePublisher(id){
            console.log('About to remove Publisher with id '+id);
            PublisherService.removePublisher(id)
                .then(
                    function(){
                        console.log('Publisher '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing publisher '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllPublishers(){
            return PublisherService.getAllPublishers();
        }

        function editPublisher(id) {
            self.successMessage='';
            self.errorMessage='';
            PublisherService.getPublisher(id).then(
                function (publisher) {
                    self.publisher = publisher;
                },
                function (errResponse) {
                    console.error('Error while removing publisher ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.publisher={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);