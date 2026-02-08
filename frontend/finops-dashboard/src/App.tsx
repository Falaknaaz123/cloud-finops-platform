import { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Box,
  Paper,
  CircularProgress,
  Alert,
  AppBar,
  Toolbar,
} from '@mui/material';
import CloudIcon from '@mui/icons-material/Cloud';
import Dashboard from './components/Dashboard';
import { CostRecord, CostSummary } from './types';
import { costApi } from './services/api';

function App() {
  const [costs, setCosts] = useState<CostRecord[]>([]);
  const [summary, setSummary] = useState<CostSummary | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      setError(null);
      const [costsData, summaryData] = await Promise.all([
        costApi.getAllCosts(),
        costApi.getCostSummary(),
      ]);
      setCosts(costsData);
      setSummary(summaryData);
    } catch (err) {
      setError('Failed to fetch cost data. Please ensure the backend is running on port 8082.');
      console.error('Error fetching data:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ flexGrow: 1, minHeight: '100vh', backgroundColor: '#f5f5f5' }}>
      <AppBar position="static" sx={{ backgroundColor: '#1976d2' }}>
        <Toolbar>
          <CloudIcon sx={{ mr: 2, fontSize: 32 }} />
          <Typography variant="h5" component="div" sx={{ flexGrow: 1, fontWeight: 600 }}>
            Cloud FinOps Platform
          </Typography>
          <Typography variant="subtitle1" sx={{ color: 'rgba(255, 255, 255, 0.7)' }}>
            Track & Optimize Cloud Spending
          </Typography>
        </Toolbar>
      </AppBar>

      <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
        {loading && (
          <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
            <CircularProgress size={60} />
          </Box>
        )}

        {error && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {error}
          </Alert>
        )}

        {!loading && !error && summary && (
          <Dashboard costs={costs} summary={summary} onRefresh={fetchData} />
        )}

        {!loading && !error && costs.length === 0 && (
          <Paper sx={{ p: 4, textAlign: 'center' }}>
            <Typography variant="h6" color="text.secondary">
              No cost data available. Please add some cost records.
            </Typography>
          </Paper>
        )}
      </Container>
    </Box>
  );
}

export default App;