function agBaOldPhotoController($scope, $http){
	
	$scope.$watch('$ng_load_plugins_before', function() {
		$scope.doOperation($scope);
	});
	
}