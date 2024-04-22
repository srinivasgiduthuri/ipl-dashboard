import { React } from "react";
import { useParams } from "react-router-dom";
import { MatchDetailCard } from "../components/MatchDetailCard";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { useQuery } from "@tanstack/react-query";
import { fetcherWithFetch } from "../lib/fetcher";

export const MatchPage = () => {
  const { teamName, year } = useParams();
  const {
    data,
    error,
    isPending: loading,
  } = useQuery({
    queryKey: ["matches", teamName],
    queryFn: () =>
      fetcherWithFetch(
        `http://localhost:8080/team/${teamName}/matches?year=${year}`
      ),
  });

  return (
    <div className="MatchPage">
      {loading && <div>Loading...</div>}
      {error && <div>Matches not found.</div>}

      {data && (
        <div>
          <h1>Matches Page</h1>
          {data.map((match) => (
            <MatchDetailCard teamName={teamName} match={match} key={match.id} />
          ))}
        </div>
      )}
    </div>
  );
};
