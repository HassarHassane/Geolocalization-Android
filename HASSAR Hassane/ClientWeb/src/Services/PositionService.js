import axios from 'axios';


export default class PositionService {

	getAllpositions(id,dateDebut,dateFin) {
		return axios.get("http://localhost:8080/positions/all/"+id+"/"+dateDebut+"/"+dateFin);
	}
}

 