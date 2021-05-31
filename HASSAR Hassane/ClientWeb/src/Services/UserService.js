import axios from 'axios';


export default class UserService {

    getAllUsers() {
		return axios.get("http://localhost:8080/users/all");
	}
	getAllAmis(id) {
		return axios.get("http://localhost:8080/users/findAmis/"+id);
	}
}

 