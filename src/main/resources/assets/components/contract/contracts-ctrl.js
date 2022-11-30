(function () {
  angular.module("contractApp").controller("ContractsCtrl", [
    "$scope",
    "ContractCRUDService",
    function ($scope, ContractCRUDService) {
      // console.log($scope.contract)

      $scope.getAllContracts = function () {
        ContractCRUDService.getAllContracts().then(
          function success(response) {
            $scope.contracts = response.data;
            $scope.message = "success";
            $scope.errorMessage = "";
          },
          function error(response) {
            $scope.message = "";
            $scope.errorMessage = "Error getting contracts!";
          }
        );
      };

      $scope.getContract = function () {
        var id = $scope.contract.id;
        ContractCRUDService.getContract($scope.contract.id).then(
          function success(response) {
            console.log(response);
            $scope.current = response.data;
            $scope.message = "success";
            $scope.errorMessage = "";
          },
          function error(response) {
            $scope.message = "";
            if (response.status === 404) {
              $scope.errorMessage = "Contract not found!";
            } else {
              $scope.errorMessage = "Error getting contract!";
            }
          }
        );
      };

      $scope.addContract = function () {
        if ($scope.contract != null && $scope.contract.code != "") {
          ContractCRUDService.addContract(
            $scope.contract.code,
            $scope.contract.description,
            $scope.contract.vendor,
            $scope.contract.contractAmount,
            $scope.contract.invoicedAmount
          ).then(
            function success(response) {
              $scope.message = "Contract added!";
              $scope.errorMessage = "";
            },
            function error(response) {
              $scope.errorMessage = "A contract with this code already exists.";
              $scope.message = "";
            }
          );
        } else {
          $scope.errorMessage = "Please enter a code!";
          $scope.message = "";
        }
      };

      $scope.updateContract = function () {
        ContractCRUDService.updateContract(
          $scope.contract.id,
          $scope.contract.code,
          $scope.contract.description,
          $scope.contract.vendor,
          $scope.contract.contractAmount,
          $scope.contract.invoicedAmount
        ).then(
          function success(response) {
            $scope.message = "Contract data updated!";
            $scope.errorMessage = "";
          },
          function error(response) {
            $scope.errorMessage = "Error updating contract!";
            $scope.message = "";
          }
        );
      };

      $scope.deleteContract = function () {
        ContractCRUDService.deleteContract($scope.contract.id).then(
          function success(response) {
            $scope.message = "Contract deleted!";
            $scope.contract = null;
            $scope.errorMessage = "";
          },
          function error(response) {
            $scope.errorMessage = "Error deleting contract!";
            $scope.message = "";
          }
        );
      };

      $scope.getTotals = function () {
        ContractCRUDService.getTotals().then(
          function success(response) {
            $scope.totals = response.data;
            $scope.errorMessage = "";
            $scope.message = "";
          },
          function error(response) {
            $scope.errorMessage = "";
            $scope.message = "";
          }
        );
      };

      $scope.getAllContracts();
      $scope.getTotals();
    },
  ]);
})();

(function () {
  angular.module("contractApp").service("ContractCRUDService", [
    "$http",
    function ($http) {
      this.getAllContracts = function getAllContracts() {
        return $http({
          method: "GET",
          url: "/api/contracts",
        });
      };

      this.getContract = function getContract(contractId) {
        return $http({
          method: "GET",
          url: "/api/contracts/" + contractId,
        });
      };

      this.addContract = function addContract(
        code,
        vendor,
        description,
        contractAmount,
        invoicedAmount
      ) {
        return $http({
          method: "POST",
          url: "/api/contracts",
          data: {
            code: code,
            vendor: vendor,
            description: description,
            contractAmount: Number(contractAmount),
            invoicedAmount: Number(invoicedAmount),
          },
        });
      };

      this.updateContract = function updateContract(
        id,
        code,
        vendor,
        description,
        contractAmount,
        invoicedAmount
      ) {
        return $http({
          method: "PUT",
          url: "/api/contracts/" + id,
          data: {
            id: id,
            code: code,
            vendor: vendor,
            description: description,
            contractAmount: Number(contractAmount),
            invoicedAmount: Number(invoicedAmount),
          },
        });
      };

      this.deleteContract = function deleteContract(id) {
        return $http({
          method: "DELETE",
          url: "/api/contracts/" + id,
        });
      };

      this.getTotals = function getTotals() {
        return $http({
          method: "GET",
          url: "/api/contracts/totals",
        });
      };
    },
  ]);
})();
