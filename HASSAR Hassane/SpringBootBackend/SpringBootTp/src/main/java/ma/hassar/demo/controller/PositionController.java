package ma.hassar.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.hassar.demo.beans.Position;
import ma.hassar.demo.repository.PositionRepository;

@RestController
@RequestMapping("positions")
public class PositionController {
	@Autowired
	private PositionRepository positionRepository;

	@PostMapping("/save")
	public void save(@RequestBody Position position) {
		positionRepository.save(position);
	}
	@CrossOrigin("*")
	@GetMapping(value = "/all/{id}/{date1}/{date2}")
	public Collection<?> findUserPositionByDate(@PathVariable(required = true) String id,
			@PathVariable(required = true) String date1, @PathVariable(required = true) String date2)
			throws NumberFormatException, ParseException {
		return positionRepository.findUserPositionByDate(Integer.parseInt(id),
				new SimpleDateFormat("yyyy-MM-dd").parse(date1), new SimpleDateFormat("yyyy-MM-dd").parse(date2));
	}

	@GetMapping(value = "/findLastPositionById/{id}")
	public Position findLastPositionById(@PathVariable(required = true) String id) {
		List<Position> positions = positionRepository.findLastPositionById(Integer.parseInt(id));
		if (positions.size() != 0) {
			return positions.get(positionRepository.findLastPositionById(Integer.parseInt(id)).size() - 1);
		}

		return null;
	}
}
