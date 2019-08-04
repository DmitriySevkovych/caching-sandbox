package caching.sandbox.models;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Country {
	@NonNull private String name;
	@NonNull private String alpha2Code;
	private Optional<String> alpha3Code = Optional.empty();
	private Optional<String> numericCode = Optional.empty();
	private Optional<String> domain = Optional.empty();
	

	@Override
	public String toString()
	{
		return this.name + " (" + this.alpha2Code + ")";
	}
}
