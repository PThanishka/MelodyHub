package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Songs;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongsService;

@Controller
public class PlaylistController {
	
	@Autowired
	PlaylistService pserv;
	@Autowired
	SongsService sserv;
	
	@GetMapping("/createplaylist")
	public String createPlaylist(Model model) {
//		Fetching the songs using song service
		List <Songs> songslist=sserv.fetchAllSongs();
//		Adding the songs in the model
		model.addAttribute("songslist",songslist);
//		Sending createplaylist
		return "createplaylist";
	}

	@PostMapping("/addplaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
//		adding playlist
		pserv.addPlaylist(playlist);
		
//		update song table
		List <Songs> songsList=playlist.getSongs();
		for(Songs song:songsList) {
			song.getPlaylist().add(playlist);
			sserv.updateSong(song);
		}
		return "playlistsuccess";
	}
	@GetMapping("/viewplaylists")
	public String viewplaylists(Model model) {
		List <Playlist> plist=pserv.fetchPlaylists();
//		System.out.println(plist);
		model.addAttribute("plist", plist);
		return "viewplaylists";
	}
}
