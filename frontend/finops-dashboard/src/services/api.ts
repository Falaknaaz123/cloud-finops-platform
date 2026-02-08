import axios from 'axios';
import { CostRecord, CostSummary } from '../types';

const API_BASE_URL = '/api';

export const costApi = {
  getAllCosts: async (): Promise<CostRecord[]> => {
    const response = await axios.get(`${API_BASE_URL}/costs`);
    return response.data;
  },

  getCostSummary: async (): Promise<CostSummary> => {
    const response = await axios.get(`${API_BASE_URL}/costs/summary`);
    return response.data;
  },

  getCostsByProvider: async (provider: string): Promise<CostRecord[]> => {
    const response = await axios.get(`${API_BASE_URL}/costs/provider/${provider}`);
    return response.data;
  },

  getCostsByService: async (service: string): Promise<CostRecord[]> => {
    const response = await axios.get(`${API_BASE_URL}/costs/service/${service}`);
    return response.data;
  },

  deleteCost: async (id: number): Promise<void> => {
    await axios.delete(`${API_BASE_URL}/costs/${id}`);
  },
};