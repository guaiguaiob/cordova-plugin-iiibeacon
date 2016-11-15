var exec = cordova.require('cordova/exec');
var iiibeacon = iiibeacon || {};
module.exports = iiibeacon;
//iiibeacon.beacons = iiibeacon.beacons || {};
//window.iiibeacons = iiibeacon.beacons;

iiibeacon.printObject = function(obj, printFun)
{
	if (!printFun) { printFun = console.log; }
	function print(obj, level)
	{
		var indent = new Array(level + 1).join('  ');
		for (var prop in obj) {
			if (obj.hasOwnProperty(prop)) {
				var value = obj[prop];
				if (typeof value == 'object') {
					printFun(indent + prop + ':');
					print(value, level + 1);
				}
				else {
					printFun(indent + prop + ': ' + value);
				}
			}
		}
	}
	print(obj, 0);
};

iiibeacon.bluetoothState = function(success, error)
{
	exec(success,
		error,
		'iiiBeacon',
		'bluetooth_bluetoothState',
		[]
	);

	return true;
};

//iiibeacon.beacons.printObject = iiibeacon.printObject;

