function validaSenhaRepetida(){
	input1 = document.getElementsByName("pass1");
	input2 = document.getElementsByName("pass2");
	p1 = input1[0];
	p2 = input2[0];
	
	if(p1.value == p2.value)
		return true;
	else{
		p1.style.borderColor = 'red';
		p2.style.borderColor = 'red';
		return false;
	}
}
