// Giới hạn min cho số 
function enforceMin(input, min) {
    if (input.value < min) {
      input.value = min;
    }
  }
// Giới hạn max cho số 

  function enforceMax(input, max) {
    if (input.value > max) {
      input.value = max;
    }
  }